package pers.elianacc.yurayura.license;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.elianacc.yurayura.util.GetMacUtil;

/**
 * 许可证书 config
 *
 * @author ELiaNaCc
 * @since 2022-10-22
 */
@Configuration
public class LicenseConfig {

    /**
     * 证书subject (使用本机mac代替)
     */
    //@Value("${license.subject}")
    private String subject;

    /**
     * 公钥别称
     */
    @Value("${license.public-alias}")
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    @Value("${license.public-store-pass}")
    private String storePass;

    /**
     * 证书生成路径
     */
    @Value("${license.license-path}")
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    @Value("${license.public-keys-store-path}")
    private String publicKeysStorePath;

    @Bean(initMethod = "installLicense", destroyMethod = "unInstallLicense")
    public LicenseVerify licenseVerify() {
        return new LicenseVerify(GetMacUtil.getMac(), publicAlias, storePass, licensePath, publicKeysStorePath);
    }

}
