Steps to push local image
Name your local images using one of these methods:

    When you build them, using docker build -t <hub-user>/<repo-name>[:<tag>]
    By re-tagging an existing local image docker tag <existing-image> <hub-user>/<repo-name>[:<tag>]
    By using docker commit <existing-container> <hub-user>/<repo-name>[:<tag>] to commit changes

Now you can push this repository to the registry designated by its name or tag.

$ docker push <hub-user>/<repo-name>:<tag>

1. cd <Path>/ci_cd/helm_deployment/spring-boot/docker
2. docker build -t 9945440831/bulletinboard:springbootv2 .
3. docker tag 9b524c89216e 9945440831/bulletinboard:springbootv2
4. ocker push 9945440831/bulletinboard:springbootv2