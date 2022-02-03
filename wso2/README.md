# WSO2 API Manager deployment with API Manager Analytics

## Prerequisites

 * Install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git), [Docker](https://www.docker.com/get-docker) and [Docker Compose](https://docs.docker.com/compose/install/#install-compose)
   in order to run the steps provided in following Quick start guide. <br><br>
 * In order to use Docker images with WSO2 updates, you need an active WSO2 subscription.
   Otherwise, you can proceed with Docker images available at [DockerHub](https://hub.docker.com/u/wso2/), which are created using GA releases.<br><br>
 * If you wish to run the Docker Compose setup using Docker images built locally, build Docker images using Docker resources
   available from [here](../../dockerfiles/) and remove the `docker.wso2.com/` prefix from the base image (i.e. `FROM` instruction) in the [Dockerfiles](dockerfiles). <br><br>

## Quick Start Guide

1. Login to WSO2's Private Docker Registry via Docker client. When prompted, enter the username and password of your WSO2 Subscription.

   ```
   docker login docker.wso2.com
   ```

2. Clone WSO2 API Management Docker and Docker Compose resource Git repository.

   ```
   git clone https://github.com/wso2/docker-apim
   ```
   
   > If you are to try out an already released zip of this repo, please ignore this 2nd step. 

3. Switch to `docker-compose/apim-with-analytics` folder.

   ```
   cd docker-apim/docker-compose/apim-with-analytics
   ```
   > If you intend to try out an already released zip of this repository, extract the zip file and directly browse to
   `docker-apim-<released-version-here>/docker-compose/apim-with-analytics` folder. 
     
   > If you intend to try out an already released tag, after executing 2nd step, checkout the relevant tag, 
    i.e. for example: `git checkout tags/v3.2.0.1`, switch to `docker-compose/apim-with-analytics` folder and continue with below steps.

4. Execute following Docker Compose command to start the deployment.

   ```
   docker-compose up --build
   ```

5. Access the WSO2 API Manager web UIs using the below URLs via a web browser.

   ```
   https://localhost:9443/publisher
   https://localhost:9443/devportal
   https://localhost:9443/admin
   https://localhost:9443/carbon
   ```
   Login to the web UIs using following credentials.
   
   * Username: admin <br>
   * Password: admin

   Please note that API Gateway will be available on following ports.
   ```
   https://localhost:8243
   https://localhost:8280
   ```
   Access the WSO2 API Manager Analytics web UIs using the below URL via a web browser.
   
   ```
   https://localhost:9643/analytics-dashboard
   ```
   Login to the web UIs using following credentials.
    
   * Username: admin <br>
   * Password: admin

## Configuration Guide

### Import Service Provider

1. Access the WSO2 API Manager Carbon web UI using the below URL via a web browser.

   ```
   https://localhost:9443/carbon
   ```
   Login to the web UI using following credentials.
   
   * Username: virtus <br>
   * Password: master

2. Navigate to the service providers list see the available service providers.

   Navigate via the side menu to the following page:   
   ```
   Identity > Service Providers > List
   ```
   > If the service provider **virtus_way** is already present, please ignore the 3rd step. 

3. Import the project's Service Provider.

   In the side menu, click the following option to add a new Service Provider:   
   ```
   Identity > Service Providers > Add
   ```

   Click the option **File Configuration** in the **Select Mode** form group.

   Select one of the service provider exported XML file in the directories below, depending on your environment:
   ```
   wso2/dockerfiles/carbon/standalone/service-providers/virtus-way.xml
   wso2/dockerfiles/carbon/dev.local/service-providers/virtus-way.xml
   wso2/dockerfiles/carbon/dev/service-providers/virtus-way.xml
   wso2/dockerfiles/carbon/prod/service-providers/virtus-way.xml //TODO
   ```

   > Use the service provider configuration in the **standalone** folder if you are running on the Integrated Development Environment (IDE).

   Click Import and wait for the Service Provider configuration screen to show up.

4. Check configuration

   Navigate to the **virtus_way** service provider configuration edit page via the service provider list, then choosing the **Edit** action.

   > You should already be in this page after a successful import

   Check the configuration under **Inbound Authentication Configuration > OAuth/OpenID Connect Configuration**.

   The **OAuth Client Key** should be the same as configured in one of the files below, depending on your environment:
   ```
   /client/portal/environments/environment.dev.local.ts
   /client/portal/environments/environment.dev.ts
   /client/portal/environments/environment.prod.ts
   /client/portal/environments/environment.ts
   ```