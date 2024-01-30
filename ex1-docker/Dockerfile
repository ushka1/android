FROM ubuntu:22.04

# set bash as default shell allowing to use source command
SHELL ["/bin/bash", "-c"]
# set timezone, required for interactive installers
ENV TZ=Europe/Warsaw
RUN ln -snf "/usr/share/zoneinfo/$TZ" /etc/localtime && echo "$TZ" > /etc/timezone

# install python 3.8 from deadsnakes ppa
RUN apt update \
  && apt install -y software-properties-common \
  && add-apt-repository -y ppa:deadsnakes/ppa \
  && apt update \
  && apt install -y python3.8 \
  && apt clean

# install sdkman dependencies
RUN apt install -y curl wget unzip zip \
  && apt clean
# install sdkman
RUN curl -s "https://get.sdkman.io" | bash

# install java 8, kotlin and gradle using sdkman
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" \
  && sdk install java 8.0.382-amzn \
  && sdk install kotlin \
  && sdk install gradle

# copy app source files
WORKDIR /app
COPY . .
# build the app
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" \
  && ./gradlew build

# expose the port
EXPOSE 8080
# start the app
CMD [ "/root/.sdkman/candidates/java/8.0.382-amzn/bin/java", "-cp", "./app/build/classes/java/main", "exercise1.App" ]