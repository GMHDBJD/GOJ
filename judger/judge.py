import os
import time
import subprocess
import redis
import json


class Judge():

    def __init__(self, host, port, db):
        self.redis = redis.Redis(host='redis', port=6379, db=0)
        self.submissionId = None
        self.code = None
        self.problemId = None
        self.language = None
        self.result = {
            'time': None,
            'memory': None,
            'result': None,
            'submissionId': None,
            'problemId': None,
            'codeLength': None,
            'userId': None,
            'contestId': None
        }

        self.languages = {
            'c': {'extension': 'c', 'compile': 'cc env/Main.c -O2 -fomit-frame-pointer -o env/Main', 'execute': 'env/Main[inputfile]'},
            'c++': {'extension': 'cpp', 'compile': 'g++ env/Main.cpp -O2 -fomit-frame-pointer -o env/Main', 'execute': 'env/Main[inputfile]'},
            'java': {'extension': 'java', 'compile': 'javac -g:none -Xlint -d env env/Main.java', 'execute': 'java -client -classpath env Main[inputfile]'},
            'python': {'extension': 'py', 'execute': 'python2 env/Main.py[inputfile]'},
            'python3': {'extension': 'py',  'execute': 'python3 env/Main.py[inputfile]'}
        }

    def runForever(self):
        while True:
            data = self.redis.rpop(1)

            if not data:
                continue

            submission = json.loads(data)
            self.submissionId = submission['submissionId']
            self.code = submission['code']
            self.problemId = submission['problemId']
            self.language = submission['language']
            self.codeFilename = 'Main'
            self.timeLimit = submission['timeLimit']
            self.memoryLimit = submission['memoryLimit']
            self.userId = submission['userId']
            self.contestId = submission['contestId']

            self.result['submissionId'] = self.submissionId
            self.result['codeLength'] = len(self.code)
            self.result['problemId'] = self.problemId
            self.result['userId'] = self.userId
            self.result['contestId'] = self.contestId

            self.judge()

    def create(self):
        os.system('rm -r env/*')
        codeFile = open(
            "env/Main." + self.languages[self.language]["extension"], "w", encoding="utf-8")
        codeFile.write(self.code.replace("\r", ""))
        codeFile.close()

    def compile(self):
        if self.language not in ('c', 'c++', 'java'):
            return

        compilecmd = self.languages[self.language]['compile']
        os.system(compilecmd)

    def execute(self):

        if self.language == 'java':
            if not os.path.exists('env/'+self.codeFilename+'.class'):
                self.result['result'] = 1
                return
        elif self.language in ('c', 'c++'):
            if not os.path.exists('env/'+self.codeFilename):
                self.result['result'] = 1
                return

        for directory in os.listdir('iocache/'+str(self.problemId)):

            inputFilename = 'iocache/' + \
                str(self.problemId)+'/'+directory+'/input.txt'
            outputFilename = 'iocache/' + \
                str(self.problemId)+'/'+directory+'/output.txt'
            resultFilename = 'env/result.txt'

            inputfile = ' <'+inputFilename+' 1 > '+resultFilename

            if self.language == 'java':
                os.system("chmod 500 .")
            else:
                os.system("chmod 100 .")

            cmd = 'ulimit -v '+str(self.memoryLimit)+'; su judge -c \"' + \
                self.languages[self.language]["execute"]+"; exit;\""

            cmd = cmd.replace("[inputfile]", inputfile)

            os.system("chmod 777 "+inputFilename)
            os.system("chmod 777 "+outputFilename)

            starttime = time.time()
            proc = subprocess.Popen([cmd], shell=True, preexec_fn=os.setsid)
            try:
                proc.communicate(timeout=self.timeLimit/1000)
                t = proc.returncode
            except subprocess.TimeoutExpired:
                self.result['time'] = self.timeLimit
                self.result['result'] = 3
                return

            endtime = time.time()

            os.system("chmod 750 .")
            os.system("pkill -u judge")

            if t != 0:
                self.result['result'] = 2
                return

            self.result['time'] = int(1000*(endtime - starttime))
            self.result['memory'] = self.memoryLimit

            if os.system('cmp ' + outputFilename+' '+resultFilename) != 0:
                self.result['result'] = 4
                return

        self.result['result'] = 5

    def judge(self):

        self.create()

        self.compile()

        if self.language in ('java', 'python', 'python3'):
            if self.language in ("java"):
                self.timeLimit *= 2
            elif self.language in ("python", "python3"):
                self.timeLimit *= 3

        self.execute()

        self.redis.lpush(2, json.dumps(self.result))


if __name__ == "__main__":
    judge = Judge(0, 0, 0)

    print("GOJ : Welcome.")
    judge.runForever()
    try:
        judge.runForever()
    except KeyboardInterrupt as e:
        print("Keyboard Interrupt Detected.\n")
    except Exception as e:
        print("Exception : "+str(e)+"\n")

    print("GOJ : Execution Protocol Terminated.\n")
