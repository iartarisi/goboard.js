(defproject goboard "0.1.0-SNAPSHOT"
  :description "Go Board/Goban library"
  :url "http://github.com/mapleoin/goboard.js"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
  		 [org.clojure/clojurescript "0.0-1586"]]
  :plugins [[lein-cljsbuild "0.2.7"]]
  :cljsbuild {:builds
              [{:source-path "src"
                :compiler {:output-to "goboard.js"
                           :optimizations :advanced
                           :pretty-print false}}]})
