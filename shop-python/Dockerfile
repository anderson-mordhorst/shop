FROM python:3.8
WORKDIR /code
COPY . .
RUN pip install -r requirements.txt
RUN cd src
EXPOSE 5000
CMD ["python3", "-m", "flask", "run", "--host=0.0.0.0"]