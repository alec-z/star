FROM servicecomb-samples/updated

RUN cd / \
  && git clone https://github.com/alec-z/star \
  && cd /star \
  && mvn -s /root/settings.xml install
WORKDIR /star
CMD  ["mysql:3306", "--", "mvn", "-s", "/root/settings.xml", "spring-boot:run"]