FROM java:openjdk-8-jdk

# The Basics
RUN apt-get update

# PostgreSQL
RUN apt-get -y install postgresql-9.4
RUN chmod 0777 /var/run/postgresql