#!/bin/sh

# Absolute path to this script
SCRIPT=$(readlink -f "$0")
# Absolute path this script is in
SCRIPT_PATH=$(dirname "$SCRIPT")

echo "Environment Variables:"
echo
echo "WSO2AM_ADDRESS=$WSO2AM_ADDRESS"
echo "WSO2AM_USERNAME=$WSO2AM_USERNAME"
echo "WSO2AM_PASSWORD=$WSO2AM_PASSWORD"
echo "WSO2_CALLBACK_URL=$WSO2_CALLBACK_URL"

API_SCOPE_PERMISSIONS="apim:api_view apim:api_create apim:api_publish apim:api_delete apim:api_import_export"

echo 'Acquiring API access token'
BASIC_AUTH=$(echo -n "${WSO2AM_USERNAME}:${WSO2AM_PASSWORD}" | base64 -w 0)
echo "BASIC_AUTH=$BASIC_AUTH"

AUTH_JSON=$(curl -X POST -H "Authorization: Basic ${BASIC_AUTH}" -H "Content-Type: application/json" -d @${SCRIPT_PATH}/publisher-api-authentication-payload.json https://${WSO2AM_ADDRESS}/client-registration/v0.17/register)
echo "AUTH_JSON=$AUTH_JSON"

WSO2_CLIENT_ID=$(echo -n ${AUTH_JSON} | jq -r '.clientId')
echo "WSO2_CLIENT_ID=$WSO2_CLIENT_ID"
WSO2_CLIENT_SECRET=$(echo -n ${AUTH_JSON} | jq -r '.clientSecret')
echo "WSO2_CLIENT_SECRET=$WSO2_CLIENT_SECRET"

WSO2_API_AUTH=$(echo -n "${WSO2_CLIENT_ID}:${WSO2_CLIENT_SECRET}" | base64 -w 0)
echo "WSO2_API_AUTH=$WSO2_API_AUTH"
API_AUTH_RESPONSE=$(curl https://${WSO2AM_ADDRESS}/gateway/token -k -H "Authorization: Basic ${WSO2_API_AUTH}" -d "grant_type=password&username=${WSO2AM_USERNAME}&password=${WSO2AM_PASSWORD}&scope=${API_SCOPE_PERMISSIONS}")
echo "API_AUTH_RESPONSE=$API_AUTH_RESPONSE"

API_ACCESS_TOKEN=$(echo -n ${API_AUTH_RESPONSE} | jq -r '.access_token')
echo "API_ACCESS_TOKEN=$API_ACCESS_TOKEN"

echo

echo "Updating %MODULE% API"
PUBLISHER_REST_API="https://${WSO2AM_ADDRESS}/api/am/publisher/v1"
WAY_API_LIST_JSON=$(curl -k -H "Authorization: Bearer ${API_ACCESS_TOKEN}" "${PUBLISHER_REST_API}/apis?query=name:way-%MODULE%")
echo "WAY_API_LIST_JSON=$WAY_API_LIST_JSON"
WAY_API_ID=$(echo -n ${WAY_API_LIST_JSON} | jq -r '.list[0].id')
echo "WAY_API_ID=$WAY_API_ID"

if [ -n "$WAY_API_ID" ] && [ "$WAY_API_ID" != null ]
then
    echo "Deleting previous API version"
    curl -k -X DELETE -H "Authorization: Bearer ${API_ACCESS_TOKEN}" "https://${WSO2AM_ADDRESS}/api/am/publisher/v1/apis/${WAY_API_ID}"
fi

echo "Creating new API version"
WAY_API_JSON=$(curl -k -X POST -H "Authorization: Bearer ${API_ACCESS_TOKEN}" -H "Content-Type: application/json" -d @${SCRIPT_PATH}/publisher-api-import-api-payload.json "https://${WSO2AM_ADDRESS}/api/am/publisher/v1/apis")
echo "WAY_API_JSON=$WAY_API_JSON"
WAY_API_ID=$(echo -n ${WAY_API_JSON} | jq -r '.id')
echo "WAY_API_ID=$WAY_API_ID"

echo "Publishing new API version"
curl -k -X POST -H "Authorization: Bearer ${API_ACCESS_TOKEN}" "https://${WSO2AM_ADDRESS}/api/am/publisher/v1/apis/change-lifecycle?apiId=${WAY_API_ID}&action=Publish"