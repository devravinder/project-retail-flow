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
   - or from root folder
   ```bash
      docker compose -f deployment/infra.yaml up -d
   ```
   
## SSL/TLS Setup - One time

- generate ssl files ( produces inside `certs/` )
  ```bash
   bash certs/generate-certs.sh localhost
  ```

- Files & purpose
   - `ca.key` / `ca.crt` :  Your local root CA 
   - `tls.key` / `tls.crt` : Server cert signed by your CA 
   - `keycloak.p12` : PKCS12 bundle (if Keycloak needs it directly) 
  
- Trust the CA in your browser** (one-time
    - macOS: `sudo security add-trusted-cert -d -r trustRoot -k /Library/Keychains/System.keychain ca.crt`
    - Ubuntu/Debian: `sudo cp ca.crt /usr/local/share/ca-certificates/myorg-ca.crt && sudo update-ca-certificates`
    - Windows: `certutil -addstore -f "ROOT" ca.crt`
    - Chrome/Firefox on Linux: Import `ca.crt` in Settings → Certificates → Authorities

## Usage

- Get token
  ```bash
     TOKENS=$(curl -X POST http://localhost:8080/realms/retail-flow/protocol/openid-connect/token -d grant_type=password -d client_id=frontend-app  -d username=super-admin  -d password=Itest@re2  -d scope=openid)
    
     ACCESS_TOKEN=$(echo $TOKENS | jq -r .access_token)
     REFRESH_TOKEN=$(echo $TOKENS | jq -r .refresh_token)
  ```

- Check access token
  ```bash
     echo $ACCESS_TOKEN
  ```

- Pass token & access api
  ```bash
    curl -X GET http://localhost:8081/api/users -H "Authorization: Bearer $ACCESS_TOKEN"
  
  ```
