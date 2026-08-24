#!/bin/sh

FILES=$(ls /usr/share/nginx/html/specs)
URLS=$(echo "$FILES" | jq -R -s -c 'split("\n") | map(select(length > 0) | {url: ("specs/" + .), name: .})')

if [ -n "$API_SERVER_URL" ]; then
  echo "Patching servers to $API_SERVER_URL"
  find /usr/share/nginx/html/specs -type f \( -name "*.yaml" -o -name "*.yml" \) -exec sed -i "/servers:/,/url:/ s|url:.*|url: $API_SERVER_URL|g" {} +
  find /usr/share/nginx/html/specs -type f -name "*.json" -exec sed -i "s|\"url\": *\"[^\"]*\"|\"url\": \"$API_SERVER_URL\"|g" {} +
fi

cat <<EOF > /usr/share/nginx/html/swagger-initializer.js
window.onload = function() {
  window.ui = SwaggerUIBundle({
    urls: $URLS,
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [SwaggerUIBundle.presets.apis, SwaggerUIStandalonePreset],
    plugins: [SwaggerUIBundle.plugins.DownloadUrl],
    layout: 'StandaloneLayout'
  });
};
EOF
