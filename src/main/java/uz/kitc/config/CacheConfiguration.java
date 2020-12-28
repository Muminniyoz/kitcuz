package uz.kitc.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, uz.kitc.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, uz.kitc.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, uz.kitc.domain.User.class.getName());
            createCache(cm, uz.kitc.domain.Authority.class.getName());
            createCache(cm, uz.kitc.domain.User.class.getName() + ".authorities");
            createCache(cm, uz.kitc.domain.SystemFiles.class.getName());
            createCache(cm, uz.kitc.domain.Courses.class.getName());
            createCache(cm, uz.kitc.domain.Courses.class.getName() + ".skills");
            createCache(cm, uz.kitc.domain.Skill.class.getName());
            createCache(cm, uz.kitc.domain.Skill.class.getName() + ".teachers");
            createCache(cm, uz.kitc.domain.Skill.class.getName() + ".courses");
            createCache(cm, uz.kitc.domain.Teacher.class.getName());
            createCache(cm, uz.kitc.domain.Teacher.class.getName() + ".skills");
            createCache(cm, uz.kitc.domain.Planning.class.getName());
            createCache(cm, uz.kitc.domain.CourseGroup.class.getName());
            createCache(cm, uz.kitc.domain.CourseRequests.class.getName());
            createCache(cm, uz.kitc.domain.Faq.class.getName());
            createCache(cm, uz.kitc.domain.Theme.class.getName());
            createCache(cm, uz.kitc.domain.Student.class.getName());
            createCache(cm, uz.kitc.domain.StudentGroup.class.getName());
            createCache(cm, uz.kitc.domain.Projects.class.getName());
            createCache(cm, uz.kitc.domain.AbilityStudent.class.getName());
            createCache(cm, uz.kitc.domain.Galereya.class.getName());
            createCache(cm, uz.kitc.domain.GalereyImages.class.getName());
            createCache(cm, uz.kitc.domain.News.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
