#!/usr/bin/env bash

sbt doc
mkdir -p "gh-pages"
cp -a "target/scala-2.11/api/" "gh-pages/"
cd "gh-pages"
git init
git config user.name "Travis CI"
git config user.email "none@none.no"
git add .
git commit -m "Deploy to GitHub Pages"
git push --force "https://radium226:${GITHUB_TOKEN}@github.com/radium226/mowitnow.git" "master:gh-pages"
