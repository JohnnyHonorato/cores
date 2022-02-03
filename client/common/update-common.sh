#!/bin/bash

LIB_PROJECT="./common"
IGNORE_BUILD="./portal"

reload_lib(){
  echo $project
  if [[ "$1" =~ ^\.\/.+$ ]];
    then
      project="$1"
    else
      project="./$1"
  fi
  cd $project
  rm -rf node_modules/common
  rm package-lock.json
  npm i
  npm cache clean --force
  cd ..
}

Help()
{
   # Display Help
   echo
   echo "Usage: ./update-common.sh [all|project...]"
   echo
   echo "options:"
   echo "	all		Update common lib for all projects."
   echo "	project...	Update common lib for specific projects."
   echo
}

while getopts ":h" option; do
   case $option in
      h) # display Help
         Help
         exit;;
   esac
done

if [ $# -eq 0 ];
  then
    Help
  else
    npm cache clean --force
    npm i
    npm run package
    cd ..
    
    if [ $1 = "all" ];
    then
      find . -maxdepth 2 -type f -name 'package.json' -print |
        sed -r 's|/[^/]+$||' |
        while read project; do
          if [ "$project" != "$LIB_PROJECT" ] && [ "$project" != "$IGNORE_BUILD" ]; then
            reload_lib $project
          fi
        done
    else
      for project in $@
        do
          if [ -d "$project" ];
            then
              reload_lib "$project"
            else
              echo "Project $project doesn't exist."
          fi
        done
    fi

    cd $LIB_PROJECT
fi

