FROM node:12.11.1-slim AS builder

ARG PORT
ARG MODULE
ARG BUILD_ENV

WORKDIR /usr/src/common

COPY ./common/package.json ./
RUN npm install
COPY ./common .

RUN npm run package
# RUN npm run package-tracker-manager
# RUN npm run package-tracker-view

WORKDIR /usr/src/app

COPY ./$MODULE/package.json ./
RUN npm install
COPY ./$MODULE .
RUN npm run build:$BUILD_ENV

FROM nginx AS deployer
ARG PORT
ARG MODULE

COPY ./$MODULE/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /usr/src/app/dist /usr/share/nginx/html

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE $PORT
