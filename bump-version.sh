#! /bin/bash
echo "Bumping up version from $1 to $2"
CHANGE_STRING="s/$1/$2/g"

/usr/bin/find . -name 'pom.xml' -exec  perl -pi -e "${CHANGE_STRING}" {} \;
