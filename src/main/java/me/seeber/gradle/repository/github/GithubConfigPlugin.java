/**
 * BSD 2-Clause License
 *
 * Copyright (c) 2016-2017, Jochen Seeber
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

import org.gradle.api.GradleException;
import org.gradle.api.Task;
import org.gradle.model.Defaults;
import org.gradle.model.Finalize;
import org.gradle.model.Model;
import org.gradle.model.ModelMap;
import org.gradle.model.Mutate;
import org.gradle.model.RuleSource;
import org.gradle.model.Validate;

import me.seeber.gradle.plugin.AbstractProjectConfigPlugin;
import me.seeber.gradle.project.base.IssueTracker;
import me.seeber.gradle.project.base.Organization;
import me.seeber.gradle.project.base.ProjectConfig;
import me.seeber.gradle.project.base.ProjectContext;
import me.seeber.gradle.project.base.Repository;
import me.seeber.gradle.repository.git.GitConfigPlugin;
import me.seeber.gradle.util.Text;

/**
 * Configure project for Github
 */
public class GithubConfigPlugin extends AbstractProjectConfigPlugin {

    /**
     * Plugin rules
     */
    public static class PluginRules extends RuleSource {

        /**
         * Provide Github configuration
         *
         * @param githubConfig Github configuration
         */
        @Model
        public void githubConfig(GithubConfig githubConfig) {
        }

        /**
         * Initialize the Github configuration
         *
         * @param githubConfig Github configuration to initialize
         * @param project Project context
         */
        @Defaults
        public void initializeGithubConfig(GithubConfig githubConfig, ProjectContext project) {
            githubConfig.setRepository(project.getName());
        }

        /**
         * Complete the Github configuration
         *
         * @param githubConfig Github configuration to complete
         */
        @Finalize
        public void completeGithubConfig(GithubConfig githubConfig) {
            if (githubConfig.getProfileUrl() == null) {
                githubConfig.setProfileUrl(Text.format("https://github.com/%s", githubConfig.getUser()));
            }

            if (githubConfig.getWebsiteUrl() == null) {
                githubConfig.setWebsiteUrl(
                        Text.format("https://github.com/%s/%s", githubConfig.getUser(), githubConfig.getRepository()));
            }

            if (githubConfig.getConnection() == null) {
                githubConfig.setConnection(Text.format("%s.git", githubConfig.getWebsiteUrl()));
            }

            if (githubConfig.getDeveloperConnection() == null) {
                githubConfig.setDeveloperConnection(
                        Text.format("git@github.com:%s/%s.git", githubConfig.getUser(), githubConfig.getRepository()));
            }

            if (githubConfig.getIssueTrackerUrl() == null) {
                githubConfig.setIssueTrackerUrl(Text.format("%s/issues", githubConfig.getWebsiteUrl()));
            }
        }

        /**
         * Validate the Github configuration
         *
         * @param githubConfig Github configuration to validate
         */
        @Validate
        public void validateGithubConfig(GithubConfig githubConfig) {
            if (githubConfig.getUser() == null) {
                throw new GradleException("Please set 'githubConfig.user'");
            }
        }

        /**
         * Initialize the project configuration
         *
         * @param projectConfig Project configuration to initialize
         * @param githubConfig Github configuration to apply
         */
        @Defaults
        public void initializeProjectConfig(ProjectConfig projectConfig, GithubConfig githubConfig) {
            if (projectConfig.getWebsiteUrl() == null) {
                projectConfig.setWebsiteUrl(githubConfig.getWebsiteUrl());
            }

            Organization organization = projectConfig.getOrganization();

            if (organization.getWebsiteUrl() == null) {
                organization.setWebsiteUrl(githubConfig.getProfileUrl());
            }

            Repository repository = projectConfig.getRepository();

            repository.setId("github");
            repository.setType("git");
            repository.setWebsiteUrl(githubConfig.getWebsiteUrl());
            repository.setConnection(githubConfig.getConnection());
            repository.setDeveloperConnection(githubConfig.getDeveloperConnection());

            IssueTracker tracker = projectConfig.getIssueTracker();

            tracker.setId("github");
            tracker.setWebsiteUrl(githubConfig.getIssueTrackerUrl());
        }

        /**
         * Create a task to create the Github repository for the project
         *
         * @param tasks Task container to create tasks
         * @param project Project context to get project settings
         */
        @Mutate
        public void createGithubTasks(ModelMap<Task> tasks, ProjectContext project) {
            tasks.create("configureGithubRepository", ConfigureGithubRepository.class, t -> {
                t.setDescription("Configure a Github repository for the project, creating it if necessary.");
                t.setGroup("build setup");
                t.setGithubToken(project.getProperty("github.token"));
                t.setRepositoryName(project.getName());
                t.setRepositoryDescription(project.getDescription());
            });

            tasks.named("configure", t -> {
                t.dependsOn("configureGithubRepository");
            });
        }
    }

    /**
     * @see me.seeber.gradle.plugin.AbstractProjectConfigPlugin#initialize()
     */
    @Override
    public void initialize() {
        getProject().getPlugins().apply(GitConfigPlugin.class);
    }
}
