#!/bin/bash
# shellcheck disable=SC2164
git pull
echo "test"
mvn test -Dcucumber.options="--tags @CHECKOUT_KK_46" -Dhost=https://goodahead:qa123123@kk-int.goodahead.dev -Dbrowser=chromer