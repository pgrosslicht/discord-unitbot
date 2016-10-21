FROM openjdk:8-jre
ADD ./build/distributions/UnitBot.tar /usr/src/
WORKDIR /usr/src/UnitBot
CMD ["/usr/src/UnitBot/bin/UnitBot"]
