# Contributing to venus-sample-pdf-fop-accessibility

Thank you for your interest in contributing to `venus-sample-pdf-fop-accessibility`.
This project is a sample repository for [Venus Fugerit Doc](https://github.com/fugerit-org/fj-doc)
demonstrating PDF/A and PDF/UA accessibility features, and these guidelines are aligned with the
upstream [fj-doc contributing guide](https://github.com/fugerit-org/fj-doc/blob/main/CONTRIBUTING.md).

Before participating, please review the [Code of Conduct](CODE_OF_CONDUCT.md).

## Ways to Contribute

Any contribution is welcome, including code improvements, tests, accessibility examples,
bug reports, and documentation updates.

- **Bug reports and enhancements**: Open an issue on
  [GitHub Issues](https://github.com/fugerit79/venus-sample-pdf-fop-accessibility/issues)
  with clear reproduction steps or a concise proposal.
- **Ideas and general questions**: Start or join a discussion in the upstream
  [fj-doc ideas forum](https://github.com/fugerit-org/fj-doc/discussions/categories/ideas).

## Pull Request Lifecycle

We actively welcome pull requests and forks. To keep reviews smooth and consistent,
follow this workflow:

1. **Fork and sync**: Fork the repository (or work from an up-to-date clone) and ensure
   your local `main` branch is synchronized with `origin/main`.
2. **Create a branch**: Create a dedicated topic branch from `main` using the
   [branch naming convention](#branch-naming).
3. **Make focused changes**: Keep each pull request focused on a single feature, fix,
   or documentation update.
4. **Verify locally**: Run the Maven build and test suite before committing to ensure
   compilation, template verification, and unit tests pass:

   ```bash
   mvn clean verify
   ```

5. **Commit your changes**: Write clear commit messages following the
   [Conventional Commits](#conventional-commits) format and the
   [Generative AI Policy](#generative-ai-policy) if applicable.
6. **Open a Pull Request**: Target the `main` branch, summarize what changed and why,
   reference any related issues, and confirm that CI checks (such as SonarQube analysis) pass.
7. **Review and merge**: Address any review comments with follow-up commits. Once approved
   and all checks are green, a maintainer will merge the pull request.

## Branch Naming

Use lowercase, hyphen-separated branch names with a short descriptive prefix matching
the type of change:

- `feat/<short-description>`: New features or accessibility samples
  (for example, `feat/pdf-ua-table-sample`)
- `fix/<short-description>`: Bug fixes (for example, `fix/watermark-contrast`)
- `docs/<short-description>`: Documentation updates
  (for example, `docs/add-contributing-guide`)
- `test/<short-description>`: Test additions or improvements
  (for example, `test/pdf-box-assertions`)
- `chore/<short-description>`: Build, dependency, or maintenance tasks
  (for example, `chore/update-fj-doc`)

## Conventional Commits

Commit messages must follow the
[Conventional Commits](https://www.conventionalcommits.org/) specification:

```text
<type>(<optional-scope>): <description>

[optional body]
```

Common commit types used in this repository:

- `feat`: A new feature or sample configuration
- `fix`: A bug fix
- `docs`: Documentation changes only
- `test`: Adding or updating tests
- `refactor`: Code restructuring without changing behavior
- `build`: Build system, plugin, or release changes
- `chore`: Dependency updates, CI configuration, or other maintenance

### Commit Message Guidelines

- Use the imperative mood in the subject line (for example, `add` instead of `added`).
- Keep the subject line concise and do not end it with a period.
- Use the commit body to explain the motivation or context for non-trivial changes.

Example:

```text
docs(guide): add CONTRIBUTING.md aligned with upstream standards

Document pull request workflow, branch naming, Conventional Commits,
and the Generative AI transparency rule.
```

## Generative AI Policy

Contributions assisted by Generative AI tools are welcome, provided that the contributor
reviews, tests, and takes full responsibility for the submitted changes (including code
quality, correctness, accessibility compliance, and license compliance).

### AI Transparency Rule

When submitting AI-assisted changes, you must include a simple informational note in the
commit message body and/or pull request description.

- **Use a plain `Note:` line** in the commit body or PR description:

  ```text
  Note: Generative AI [VENDOR] assistance was used [to DETAIL ON THE CONTRIBUTION].
  ```

- **Do not use `Co-authored-by` git tags** for AI tools or models. Avoid trailers such
  as `Co-authored-by: AI Assistant <...>` in commit messages.

Examples of acceptable notes:

```text
Note: Generative AI (Google Gemini) assistance was used to improve test coverage and documentation.
```

```text
Note: Generated with AI following Fugerit contribution guidelines.
```

## License

By contributing to
[venus-sample-pdf-fop-accessibility](https://github.com/fugerit79/venus-sample-pdf-fop-accessibility),
you agree that your contributions will be licensed under the [LICENSE](LICENSE) file in
the root directory of this source tree.
