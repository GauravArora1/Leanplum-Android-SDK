FROM python:2.7.15

WORKDIR /tmp

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt