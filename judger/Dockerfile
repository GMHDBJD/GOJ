# DOCKER-VERSION 1.12.0
FROM ubuntu:latest
RUN apt-get update && apt-get install -y \
  vim \
  openjdk-8-jdk \
  g++ \
  python \
  python3 \
  python3-pip
COPY requirements.txt /tmp/
RUN pip3 install --requirement /tmp/requirements.txt
RUN chmod 700 /tmp
RUN useradd -m -u 8723 -s /bin/bash judge
WORKDIR /home/judge
RUN mkdir env env/io_cache
RUN chmod 755 env
RUN chmod 700 env/io_cache
RUN chown judge env
RUN chgrp judge env
COPY judge.py /home/judge/
EXPOSE 8723
CMD ["python3", "judge.py"]