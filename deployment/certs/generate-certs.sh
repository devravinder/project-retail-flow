#!/bin/bash
# ============================================================
# Generate self-signed SSL certs for local/dev production-like
# setup. For real production, replace with Let's Encrypt certs.
# ============================================================

set -e

DOMAIN="${1:-localhost}"
DAYS=825   # max accepted by modern browsers

echo ">>> Generating CA key & cert..."
openssl genrsa -out ca.key 4096
openssl req -new -x509 -days $DAYS -key ca.key -out ca.crt \
  -subj "/C=IN/ST=Karnataka/L=Bengaluru/O=MyOrg CA/CN=MyOrg Root CA"

echo ">>> Generating server key..."
openssl genrsa -out tls.key 2048

echo ">>> Generating CSR..."
openssl req -new -key tls.key -out tls.csr \
  -subj "/C=IN/ST=Karnataka/L=Bengaluru/O=MyOrg/CN=${DOMAIN}"

echo ">>> Creating SAN extension file..."
cat > tls.ext <<EOF
authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names

[alt_names]
DNS.1 = ${DOMAIN}
DNS.2 = *.${DOMAIN}
DNS.3 = keycloak
DNS.4 = localhost
IP.1  = 127.0.0.1
EOF

echo ">>> Signing server cert with CA..."
openssl x509 -req -in tls.csr -CA ca.crt -CAkey ca.key \
  -CAcreateserial -out tls.crt -days $DAYS -extfile tls.ext

echo ">>> Converting to PKCS12 (Keycloak needs this)..."
openssl pkcs12 -export \
  -in tls.crt \
  -inkey tls.key \
  -out keycloak.p12 \
  -name keycloak \
  -CAfile ca.crt \
  -caname root \
  -passout pass:keystorepassword

echo ""
echo "✅ Certificates generated:"
ls -lh *.crt *.key *.p12 2>/dev/null
echo ""
echo "📌 To trust this CA on your machine:"
echo "   macOS : sudo security add-trusted-cert -d -r trustRoot -k /Library/Keychains/System.keychain ca.crt"
echo "   Ubuntu: sudo cp ca.crt /usr/local/share/ca-certificates/myorg-ca.crt && sudo update-ca-certificates"
echo "   Windows: certutil -addstore -f 'ROOT' ca.crt"
