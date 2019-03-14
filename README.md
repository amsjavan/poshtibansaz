Poshtibansaz Bot
==================================================================

### Deployment

* Increases version in version.sbt file
* Creates markdown version note
* Publish local docker 

    ```./sbt docker:publishLocal```

* Login and push 

    ```docker login docker.bale.ai ```

    ```docker push  docker.bale.ai/hackathon/grpc:0.1.0```