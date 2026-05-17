#!/bin/bash

# Exit on error
set -e

echo "🚀 Updating Flutter API Client..."

# 1. Update/Generate OpenAPI Spec in Backend
echo "📡 Generating latest OpenAPI spec..."
./mvnw test -Dtest=OpenApiGeneratorTest

# 2. Check if Flutter project exists
FLUTTER_PATH="../orbit-application"
if [ ! -d "$FLUTTER_PATH" ]; then
  echo "❌ Error: Flutter project not found at $FLUTTER_PATH"
  exit 1
fi

# 3. Copy spec to Flutter project
echo "📂 Copying spec to Flutter project..."
cp docs/public/openapi.json "$FLUTTER_PATH/orbit-api-spec.json"

# 4. Generate Dart Client via bunx (openapi-generator)
echo "🛠️ Generating Dart API Client..."
bunx @openapitools/openapi-generator-cli generate \
  -i "$FLUTTER_PATH/orbit-api-spec.json" \
  -g dart-dio \
  -o "$FLUTTER_PATH/lib/core/network/api" \
  --additional-properties=pubName=orbit_api_client,serializationLibrary=json_serializable

# 5. Run build_runner in Flutter project to generate g.dart files
echo "⚙️ Running build_runner in Flutter project..."
cd "$FLUTTER_PATH"
flutter pub get
dart run build_runner build --delete-conflicting-outputs

echo "✅ Flutter API Client updated successfully!"
