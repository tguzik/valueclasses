{
  pkgs,
  lib,
  config,
  inputs,
  ...
}:
{
  # See full reference at https://devenv.sh/reference/options/

  # https://devenv.sh/languages/
  languages = {
    java = {
      enable = true;
      jdk.package = pkgs.jdk17;
      maven.enable = true;
    };
  };

  # https://devenv.sh/packages/
  # https://search.nixos.org/packages
  # Note that the JDK and Maven are already pulled in through the `languages.java` props.
  packages = [
    # Basic utilities
    pkgs.git
    pkgs.curl
    pkgs.go-task
  ];

  git-hooks.hooks = {
    actionlint.enable = true;
    gitlint.enable = true;
    markdownlint = {
      enable = true;
      settings.configuration = {
        MD013 = {
          line_length = 120;
        };
        MD033 = false;
        MD034 = false;
      };
    };
    nixfmt.enable = true;
    no-commit-to-branch.enable = true;
    shellcheck.enable = true;
    trufflehog.enable = true;
    yamllint = {
      enable = true;
      settings.configuration = ''
        extends: relaxed
        rules:
          line-length:
            max: 150
      '';
    };
  };

  enterShell = ''
    # Do nothing
  '';

  enterTest = ''
    # Do nothing
  '';
}
