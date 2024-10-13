FROM ubuntu:latest
LABEL authors="alvaro"

ENTRYPOINT ["top", "-b"]