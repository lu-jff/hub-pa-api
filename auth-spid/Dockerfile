FROM circleci/node:10.14.2 as builder

WORKDIR /home/circleci

COPY src src
COPY package.json package.json
COPY tsconfig.json tsconfig.json
COPY yarn.lock yarn.lock

RUN mkdir certs \
  && openssl req -nodes \
  -new \
  -x509 \
  -sha256 \
  -days 365 \
  -newkey rsa:2048 \
  -subj "/C=IT/ST=State/L=City/O=Acme Inc. /OU=IT Department/CN=spid-express.selfsigned.example" \
  -keyout certs/key.pem \
  -out certs/cert.pem \
  && yarn install \
  && yarn build

FROM node:10.14.2-alpine
LABEL maintainer="https://developers.italia.it"

WORKDIR /usr/src/app

COPY /package.json /usr/src/app/package.json
COPY --from=builder /home/circleci/src /usr/src/app/src
COPY --from=builder /home/circleci/dist /usr/src/app/dist
COPY --from=builder /home/circleci/certs /usr/src/app/certs
COPY --from=builder /home/circleci/node_modules /usr/src/app/node_modules

EXPOSE 3000

ENV METADATA_PUBLIC_CERT="./certs/cert.pem"
ENV METADATA_PRIVATE_CERT="./certs/key.pem"

ENV ORG_ISSUER="https://spid.agid.gov.it/cd"
ENV ORG_URL="http://localhost:3000"
ENV ORG_DISPLAY_NAME="Organization display name"
ENV ORG_NAME="Organization name"

ENV AUTH_N_CONTEXT="https://www.spid.gov.it/SpidL2"

ENV SPID_ATTRIBUTES="address,email,name,familyName,fiscalNumber,mobilePhone"

ENV ENDPOINT_ACS="/acs"
ENV ENDPOINT_ERROR="/error"
ENV ENDPOINT_SUCCESS="/success"
ENV ENDPOINT_LOGIN="/login"
ENV ENDPOINT_METADATA="/metadata"
ENV ENDPOINT_LOGOUT="/logout"


ENV SPID_VALIDATOR_URL="http://localhost:8080"
ENV SPID_TESTENV_URL="https://spid-testenv2:8088"

ENV REDIS_HOST="redis"

CMD ["yarn", "dev"]
