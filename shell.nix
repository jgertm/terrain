{ pkgs ? import <nixpkgs> {} }:

(pkgs.buildFHSUserEnv {
  name = "terrain-env";
  targetPkgs = pkgs: (with pkgs;
    [ openjdk clojure leiningen
      javaPackages.jogl_2_3_2
      xorg.libXxf86vm
    ]);
  runScript = ''
    lein \
      update-in :dependencies conj \[nrepl\ \"0.6.0\"\] -- \
      update-in :plugins conj \[refactor-nrepl\ \"2.5.0-SNAPSHOT\"\] -- \
      update-in :plugins conj \[cider/cider-nrepl\ \"0.22.0-beta8\"\] -- \
      repl :headless :host localhost
  '';
}).env
