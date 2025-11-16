{ pkgs, lib, config, inputs, ... }:
{
  # https://devenv.sh/languages/
  languages = {
    java = {
      enable = true;
      jdk.package = pkgs.jdk11;
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

    # Additional linters
    pkgs.actionlint
  ];

  enterShell = ''
    # Do nothing
  '';

  enterTest = ''
    # Do nothing
  '';

  # https://devenv.sh/git-hooks/
  # git-hooks.hooks.shellcheck.enable = true;

  # See full reference at https://devenv.sh/reference/options/
}
