#!/bin/bash
# if given params size is 1
if [ ! $# -eq 1  ];then
  echo "[mkdir.sh] param error!"
  exit 1
fi

# check dir exists, if not, make dir 
dirname=$1
echo "[mkdir.sh] the dir name is: $dirname"
if [ ! -d $dirname  ];then
  echo "[mkdir.sh] create dir: $dirname"
  mkdir $dirname
else
  echo "[mkdir.sh] dir exist"
fi
