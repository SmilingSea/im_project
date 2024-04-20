package com.jiang.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;

import java.io.File;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 对象存储工具类
 */
public class COSUtils {
    public static String UploadPicture(File file) {


        TreeMap<String, Object> config = new TreeMap<String, Object>();

        try {
            //这里的 SecretId 和 SecretKey 代表了用于申请临时密钥的永久身份（主账号、子账号等），子账号需要具有操作存储桶的权限。
            // TODO:id 和 key 需要解耦
            String secretId = "************************************";
            String secretKey = "***********************************";
            // 替换为您的云 api 密钥 SecretId
            config.put("secretId", secretId);
            // 替换为您的云 api 密钥 SecretKey
            config.put("secretKey", secretKey);

            // 设置域名:
            // 如果您使用了腾讯云 cvm，可以设置内部域名
            //config.put("host", "sts.internal.tencentcloudapi.com");

            // 临时密钥有效时长，单位是秒，默认 1800 秒，目前主账号最长 2 小时（即 7200 秒），子账号最长 36 小时（即 129600）秒
            config.put("durationSeconds", 1800);

            // 换成您的 bucket
            config.put("bucket", "improject-1322480945");
            // 换成 bucket 所在地区
            config.put("region", "ap-nanjing");

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径
            // 列举几种典型的前缀授权场景：
            // 1、允许访问所有对象："*"
            // 2、允许访问指定的对象："a/a1.txt", "b/b1.txt"
            // 3、允许访问指定前缀的对象："a*", "a/*", "b/*"
            // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
            config.put("allowPrefixes", new String[]{
                    "*"
            });

            // 密钥的权限列表。必须在这里指定本次临时密钥所需要的权限。
            // 简单上传、表单上传和分块上传需要以下的权限，其他权限列表请参见 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[]{
                    // 简单上传
                    "name/cos:PutObject",
                    // 表单上传、小程序上传
                    "name/cos:PostObject",
                    // 分块上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);

            Response response = CosStsClient.getCredential(config);
            // System.out.println(response.credentials.tmpSecretId);
            // System.out.println(response.credentials.tmpSecretKey);
            // System.out.println(response.credentials.sessionToken);

            // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
            String tmpSecretId = response.credentials.tmpSecretId;
            String tmpSecretKey = response.credentials.tmpSecretKey;
            String sessionToken = response.credentials.sessionToken;
            BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
            // 2 设置 bucket 的地域
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分
            Region region = new Region("ap-nanjing"); //COS_REGION 参数：配置成存储桶 bucket 的实际地域，例如 ap-beijing，更多 COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
            ClientConfig clientConfig = new ClientConfig(region);
            // 3 生成 cos 客户端
            COSClient cosClient = new COSClient(cred, clientConfig);
            ExecutorService threadPool = Executors.newFixedThreadPool(16);


            // 指定文件将要存放的存储桶
            String bucketName = "improject-1322480945";
// 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            long id = IdWorker.getId();
            String key = "picture/" + id + ".png";

            TransferManager transferManager = new TransferManager(cosClient, threadPool);
            TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
            transferManagerConfiguration.setMultipartUploadThreshold(10 * 1024 * 1024);
            transferManagerConfiguration.setMinimumUploadPartSize(10 * 1024 * 1024);

            transferManager.setConfiguration(transferManagerConfiguration);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            return "https://improject-1322480945.cos.ap-nanjing.myqcloud.com/" + key;
        } catch (Exception e) {
            // TODO : 日志打印成文件
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }
}
