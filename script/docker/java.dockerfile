FROM servicecomb-samples/updated

RUN cd / \
  && git clone https://github.com/alec-z/star \
  && cd /star \
  && mvn -s /root/settings.xml install
COPY ./api_token.secret /star/src/main/resources
WORKDIR /star
CMD  ["mysql:3306", "--", "mvn", "-s", "/root/settings.xml", "spring-boot:run"]