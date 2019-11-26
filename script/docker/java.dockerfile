FROM servicecomb-samples/updated

RUN git clone https://github.com/alec-z/star \
  && cd star \
  && mvn -s /root/settings.xml install
WORKDIR /star
CMD ["mvn -s /root/settings.xml spring-boot:run"]