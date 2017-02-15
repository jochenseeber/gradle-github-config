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

import org.eclipse.jdt.annotation.Nullable;
import org.gradle.model.Managed;

/**
 * Github configuration
 */
@Managed
public interface GithubConfig {

    /**
     * Get the Github user
     * 
     * @return Github user
     */
    public @Nullable String getUser();

    /**
     * Set the Github user
     * 
     * @param user Github user
     */
    public void setUser(@Nullable String user);

    /**
     * Get the repository name
     * 
     * @return Repository name
     */
    public @Nullable String getRepository();

    /**
     * Set the repository name
     * 
     * @param name Repository name
     */
    public void setRepository(@Nullable String name);

    /**
     * Get the URL of the project web site
     * 
     * @return URL of the project web site
     */
    public @Nullable String getWebsiteUrl();

    /**
     * Set the URL of the project web site
     * 
     * @param url URL of the project web site
     */
    public void setWebsiteUrl(@Nullable String url);

    /**
     * Get the URL of the issue tracker
     * 
     * @return URL of the issue tracker
     */
    public @Nullable String getIssueTrackerUrl();

    /**
     * Set the URL of the issue tracker
     * 
     * @param url URL of the issue tracker
     */
    public void setIssueTrackerUrl(@Nullable String url);

    /**
     * Get the repository connection
     * 
     * @return Repository connection
     */
    public @Nullable String getConnection();

    /**
     * Set the repository connection
     * 
     * @param connection Repository connection
     */
    public void setConnection(@Nullable String connection);

    /**
     * Get the repository developer connection
     * 
     * @return Repository developer connection
     */
    public @Nullable String getDeveloperConnection();

    /**
     * Set the repository developer connection
     * 
     * @param connection Repository developer connection
     */
    public void setDeveloperConnection(@Nullable String connection);

    /**
     * Get the URL of the developer profile
     * 
     * @return URL of the developer profile
     */
    public @Nullable String getProfileUrl();

    /**
     * Set the URL of the developer profile
     * 
     * @param url URL of the developer profile
     */
    public void setProfileUrl(@Nullable String url);

}
