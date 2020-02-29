import os
import sys
import time
import subprocess
import redis
import json


def file_read(filename):
    f = open(filename, "r", encoding="utf-8")
    d = f.read()
    f.close()
    return d.replace("\r", "")


class Judge():

    def __init__(self, host, port, db):
        self.redis = redis.Redis(host='localhost', port=6379, db=0)
        self.submissionId = None
        self.code = None
        self.problemId = None
        self.language = None
        self.result = {
            'time': None,
            'memory': None,
            'result': None,
            'submissionId': None
        }

        self.languages = {
            'c': {'extension': 'c', 'compile': 'cc env/code.c -O2 -fomit-frame-pointer -o env/code', 'execute': 'env/code[inputfile]'},
            'c++': {'extension': 'cpp', 'compile': 'g++ env/code.cpp -O2 -fomit-frame-pointer -o env/code', 'execute': 'env/code[inputfile]'},
            'java': {'extension': 'java', 'compile': 'javac -g:none -Xlint -d env env/code.java', 'execute': 'java -client -classpath env code[inputfile]'},
            'python': {'extension': 'py', 'execute': 'python2 env/code.py[inputfile]'},
            'python3': {'extension': 'py',  'execute': 'python3 env/code.py[inputfile]'}
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
            self.codeFilename = 'code'
            self.timeLimit = submission['timeLimit']
            self.timeLimit = 1000
            self.memoryLimit = submission['memoryLimit']

            self.result['submissionId'] = self.submissionId

            self.judge()

    def create(self):
        os.system('rm -r env/*')
        codeFile = open(
            "env/code." + self.languages[self.language]["extension"], "w", encoding="utf-8")
        codeFile.write(self.code.replace("\r", ""))
        codeFile.close()

    def compile(self):
        if(self.language not in ('c', 'c++', 'java')):
            return

        compilecmd = self.languages[self.language]['compile']
        print(compilecmd)
        os.system(compilecmd)

    def execute(self):

        if self.language == 'java':
            if not os.path.exists('env/'+self.codeFilename+'.class'):
                self.result['result'] = 'CE'
                return
        else:
            if not os.path.exists('env/'+self.codeFilename):
                self.result['result'] = 'CE'
                return

        for directory in os.listdir('iocache/'+str(self.problemId)):

            inputFilename = 'iocache/' + \
                str(self.problemId)+'/'+directory+'/input.txt'
            outputFilename = 'iocache/' + \
                str(self.problemId)+'/'+directory+'/output.txt'
            resultFilename = 'env/result.txt'

            inputfile = ' <'+inputFilename+' 1 > '+resultFilename

            os.system("chmod 100 .")

            cmd = 'ulimit -v '+str(self.memoryLimit)+'; su judge -c \"' +  self.languages[self.language]["execute"]+"; exit;\""

            cmd = cmd.replace("[inputfile]", inputfile)

            os.system("chmod 777 "+inputFilename)
            os.system("chmod 777 "+outputFilename)
            os.system("chmod 777 "+resultFilename)

            starttime = time.time()
            proc = subprocess.Popen([cmd], shell=True, preexec_fn=os.setsid)
            try:
                proc.communicate(timeout=self.timeLimit/1000)
                t = proc.returncode
            except subprocess.TimeoutExpired:
                self.result['time'] = self.timeLimit
                self.result['result'] = 'TLE'
                return

            endtime = time.time()

            os.system("chmod 750 .")
            os.system("pkill -u judge")

            if t != 0:
                self.result['result'] = "RTE"
                return

            self.result['time'] = int(1000*(endtime - starttime))
            self.result['memory'] = self.memoryLimit

            if os.system('cmp ' + outputFilename+' '+resultFilename) != 0:
                self.result['result'] = 'WA'
                return

        self.result['result'] = 'AC'

    def judge(self):

        self.create()

        self.compile()

        ''' if self.language in ('java', 'python', 'python3'):
            if self.language in ("java"):
                self.timeLimit *= 2
            elif self.language in ("python", "python3"):
                self.timeLimit *= 3
        '''

        self.execute()

        self.redis.lpush(2, json.dumps(self.result))


if __name__ == "__main__":
    judge = Judge(0, 0, 0)

    judge.runForever()
    try:
        judge.runForever()
    except KeyboardInterrupt as e:
        print(" Keyboard Interrupt Detected.\n")
    except Exception as e:
        print("Exception : "+str(e)+"\n")

    print("GOJ : Execution Protocol Terminated.\n")
