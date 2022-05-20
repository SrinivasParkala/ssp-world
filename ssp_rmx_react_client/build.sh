echo rm -rf build
rm -rf build

echo npm run build
npm run build

echo clean and create target folders
rm -rf ./target/*
mkdir ./target/uipackage
mkdir ./target/uipackage/ui-project
mkdir ./target/uipackage/proxy

echo build package
cp src/proxy/* ./target/uipackage/proxy/
cp src/proxy/.env ./target/uipackage/.env
cp -r build ./target/uipackage/ui-project/build
cp startup.js ./target/uipackage/ui-project/
cp startUI.sh ./target/uipackage/

echo Packaging done
