#!/bin/bash

echo "# inital_task" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/shefoolinux/inital_task.git
git push -u origin main