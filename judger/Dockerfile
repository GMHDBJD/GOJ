# DOCKER-VERSION 1.12.0
FROM alpine:3.10

RUN apk add --no-cache \
    openjdk8 g++ python python3 py3-pip

COPY requirements.txt /tmp/
RUN pip3 install --requirement /tmp/requirements.txt
RUN chmod 700 /tmp
RUN adduser -u 8723 -s /bin/bash --disabled-password judge
WORKDIR /home/judge
RUN mkdir env iocache
RUN chmod 755 env
RUN chmod 700 iocache
RUN chown judge env
RUN chown judge iocache
RUN chgrp judge env
RUN chgrp judge iocache
COPY judge.py /home/judge/
EXPOSE 8723
CMD ["python3", "-u", "judge.py"]