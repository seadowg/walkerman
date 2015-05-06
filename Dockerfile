FROM java:openjdk-8-jdk

# The Basics
RUN apt-get update

# Gradle
RUN wget https://services.gradle.org/distributions/gradle-2.2.1-all.zip && \
    unzip gradle-2.2.1-all.zip && \
    ln -s gradle-2.2.1 /usr/bin/gradle && \
    rm gradle-2.2.1-all.zip

ENV GRADLE_HOME /usr/bin/gradle
ENV PATH $PATH:$GRADLE_HOME/bin

# PostgreSQL
RUN apt-get -y install postgresql-9.4
RUN chmod 0777 /var/run/postgresql