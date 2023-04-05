package pers.elianacc.yurayura.license;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;
import org.lee.license.manager.CustomLicenseManager;
import org.lee.license.param.CustomKeyStoreParam;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * 许可证书校验类
 *
 * @author ELiaNaCc
 * @since 2022-10-22
 */
@Slf4j
public class LicenseVerify {

    /**
     * 证书subject
     */
    private final String subject;
    /**
     * 公钥别称
     */
    private final String publicAlias;
    /**
     * 访问公钥库的密码
     */
    private final String storePass;
    /**
     * 证书生成路径
     */
    private final String licensePath;
    /**
     * 密钥库存储路径
     */
    private final String publicKeysStorePath;
    /**
     * LicenseManager
     */
    private LicenseManager licenseManager;
    /**
     * 标识证书是否安装成功
     */
    private boolean installSuccess;

    public LicenseVerify(String subject, String publicAlias, String storePass, String licensePath, String publicKeysStorePath) {
        this.subject = subject;
        this.publicAlias = publicAlias;
        this.storePass = storePass;
        this.licensePath = licensePath;
        this.publicKeysStorePath = publicKeysStorePath;
    }

    /**
     * 安装License证书，读取证书相关的信息, 在bean加入容器的时候自动调用
     */
    public void installLicense() {
        try {
            Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);

            CipherParam cipherParam = new DefaultCipherParam(storePass);

            KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerify.class,
                    publicKeysStorePath,
                    publicAlias,
                    storePass,
                    null);
            LicenseParam licenseParam = new DefaultLicenseParam(subject, preferences, publicStoreParam, cipherParam);

            licenseManager = new CustomLicenseManager(licenseParam);
            licenseManager.uninstall();
            LicenseContent licenseContent = licenseManager.install(new File(licensePath));
            installSuccess = true;
            log.info("------------------------------- 许可证书验证成功 -------------------------------");
            log.info("证书有效期：{} - {}", DateUtil.format(licenseContent.getNotBefore(), DatePattern.NORM_DATETIME_PATTERN), DateUtil.format(licenseContent.getNotAfter(), DatePattern.NORM_DATETIME_PATTERN));
        } catch (Exception e) {
            installSuccess = false;
            e.printStackTrace();
            log.error("------------------------------- 许可证书验证失败 -------------------------------");
            log.error("证书已经超期或不是您的证书");
            System.exit(0);
        }
    }

    /**
     * 卸载证书，在bean从容器移除的时候自动调用
     */
    public void unInstallLicense() {
        if (installSuccess) {
            try {
                licenseManager.uninstall();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 校验License证书
     */
    public boolean verify() {
        try {
            licenseManager.verify();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
