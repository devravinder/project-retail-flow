# Setup

## Tools Uses
1. Used in the project
   - docker
   - mongodb (docker container)
   - postgres (docker container)
   - redis (docker container)
2. Utility tools - not part of project
    - portainer (docker container)
    - mongoDB compass
    - redis insight
    - dbeaver
    - lens

## Local Setup
1. Infra setup
   - run the cmd from infra folder
     ```bash
       docker compose -f infra.yaml up -d
     ```
   - or from here
     ```bash
        docker compose -f ../deployment/infra.yaml up -d
     ```