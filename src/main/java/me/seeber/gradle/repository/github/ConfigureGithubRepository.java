/**
 * BSD 2-Clause License
 *
 * Copyright (c) 2016, Jochen Seeber
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package me.seeber.gradle.repository.github;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.annotation.Nullable;
import org.gradle.api.internal.ConventionTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

/**
 * Create a Github repository for the project
 */
public class ConfigureGithubRepository extends ConventionTask {

    /**
     * Name of the repository
     */
    @Input
    private @Nullable String repositoryName;

    /**
     * Description of the repository
     */
    @Input
    private @Nullable String repositoryDescription;

    /**
     * Github access token
     *
     * Create a <a href="https://github.com/settings/tokens">personal access token</a>.
     */
    @Input
    private @Nullable String githubToken;

    /**
     * Create the repository on Github
     *
     * @throws IOException if something goes horribly wrong
     */
    @TaskAction
    public void configureRepository() throws IOException {
        GitHub github = GitHub.connect(null, getGithubToken());
        String user = github.getMyself().getLogin();
        GHRepository repository = null;

        try {
            repository = github.getRepository(user + "/" + getRepositoryName());
        }
        catch (FileNotFoundException e) {
            GHCreateRepositoryBuilder repositoryCreator = github.createRepository(getRepositoryName());
            repository = repositoryCreator.create();
        }

        String description = getRepositoryDescription();

        if (description != null && !description.equals(repository.getDescription())) {
            repository.setDescription(description);
        }
    }

    /**
     * Get the name of the repository
     *
     * @return Name of the repository
     */
    public @Nullable String getRepositoryName() {
        return this.repositoryName;
    }

    /**
     * Set the name of the repository
     *
     * @param name Name of the repository
     */
    public void setRepositoryName(@Nullable String name) {
        this.repositoryName = name;
    }

    /**
     * Get the description of the repository
     *
     * @return Description of the repository
     */
    public @Nullable String getRepositoryDescription() {
        return this.repositoryDescription;
    }

    /**
     * Set the description of the repository
     *
     * @param description Description of the repository
     */
    public void setRepositoryDescription(@Nullable String description) {
        this.repositoryDescription = description;
    }

    /**
     * Get the Github access token
     *
     * @return Github access token
     */
    public @Nullable String getGithubToken() {
        return this.githubToken;
    }

    /**
     * Set the Github access token
     *
     * @param githubToken Github access token
     */
    public void setGithubToken(@Nullable String githubToken) {
        this.githubToken = githubToken;
    }

}
