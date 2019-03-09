package digital.mercy.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(digital.mercy.domain.Member.class.getName(), jcacheConfiguration);
            cm.createCache(digital.mercy.domain.Member.class.getName() + ".logins", jcacheConfiguration);
            cm.createCache(digital.mercy.domain.FavoriteProject.class.getName(), jcacheConfiguration);
            cm.createCache(digital.mercy.domain.FavoriteProject.class.getName() + ".projects", jcacheConfiguration);
            cm.createCache(digital.mercy.domain.Investment.class.getName(), jcacheConfiguration);
            cm.createCache(digital.mercy.domain.Project.class.getName(), jcacheConfiguration);
            cm.createCache(digital.mercy.domain.Project.class.getName() + ".projectNames", jcacheConfiguration);
            cm.createCache(digital.mercy.domain.Measure.class.getName(), jcacheConfiguration);
            cm.createCache(digital.mercy.domain.Challenge.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
