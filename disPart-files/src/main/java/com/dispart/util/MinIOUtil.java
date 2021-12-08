package com.dispart.util;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄贵川
 * @date 2021-09-27
 */
@Slf4j
@Component
public class MinIOUtil {
    @Autowired
    private MinioClient minioClient;

    private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    public boolean bucketExists(String bucketName) {
        try {
            // 检查存储桶是否存在
            boolean flag = minioClient.bucketExists(bucketName);
            if (flag) {
                return true;
            }
        } catch (Exception e) {
            log.error("检查存储桶是否存在异常", e);
            return false;
        }
        return false;
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    public boolean makeBucket(String bucketName) {
        try {
            boolean flag = bucketExists(bucketName);
            if (!flag) {
                minioClient.makeBucket(bucketName);
                return true;
            }
        } catch (Exception e) {
            log.error("创建存储桶异常", e);
            return false;
        }
        return false;
    }

    /**
     * 列出所有存储桶名称
     *
     * @return List<String>
     */
    public List<String> listBucketNames() {
        try {
            List<Bucket> bucketList = listBuckets();
            List<String> bucketListName = new ArrayList<>();
            for (Bucket bucket : bucketList) {
                bucketListName.add(bucket.name());
            }
            return bucketListName;
        } catch (Exception e) {
            log.error("列出所有存储桶名称异常", e);
            return null;
        }
    }

    /**
     * 列出所有存储桶
     *
     * @return List<Bucket>
     */
    public List<Bucket> listBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            log.error("列出所有存储桶异常", e);
            return null;
        }
    }

    /**
     * 删除存储桶
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    public boolean removeBucket(String bucketName) {
        try {
            boolean flag = bucketExists(bucketName);
            if (flag) {
                Iterable<Result<Item>> myObjects = listObjects(bucketName);
                for (Result<Item> result : myObjects) {
                    Item item = result.get();
                    // 有对象文件，则删除失败
                    if (item.size() > 0) {
                        return false;
                    }
                }
                // 删除存储桶，注意，只有存储桶为空时才能删除成功。
                minioClient.removeBucket(bucketName);
                flag = bucketExists(bucketName);
                if (!flag) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("删除存储桶异常", e);
            return false;
        }
    }

    /**
     * 列出存储桶中的所有对象名称
     *
     * @param bucketName 存储桶名称
     * @return List<String>
     */
    public List<String> listObjectNames(String bucketName) {
        List<String> listObjectNames = new ArrayList<>();
        try {
            boolean flag = bucketExists(bucketName);
            if (flag) {
                Iterable<Result<Item>> myObjects = listObjects(bucketName);
                for (Result<Item> result : myObjects) {
                    Item item = result.get();
                    listObjectNames.add(item.objectName());
                }
            }
        } catch (Exception e) {
            log.error("列出存储桶中的所有对象名称异常", e);
            return listObjectNames;
        }
        return listObjectNames;
    }

    /**
     * 列出存储桶中的所有对象
     *
     * @param bucketName 存储桶名称
     * @return Iterable<Result < Item>>
     */
    public Iterable<Result<Item>> listObjects(String bucketName) {
        try {
            boolean flag = bucketExists(bucketName);
            if (flag) {
                return minioClient.listObjects(bucketName);
            }
        } catch (XmlParserException e) {
            log.error("列出存储桶中的所有对象异常", e);
            return null;
        }
        return null;
    }

    /**
     * 通过文件上传到对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name
     * @return boolean
     */
    public boolean putObject(String bucketName, String objectName, String fileName) {
        try {
            boolean flag = bucketExists(bucketName);
            if (flag) {
                minioClient.putObject(bucketName, objectName, fileName, null);
                ObjectStat statObject = statObject(bucketName, objectName);
                if (statObject != null && statObject.length() > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("通过文件上传到对象异常", e);
            return false;
        }
        return false;

    }

    /**
     * 通过InputStream上传对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param stream     要上传的流
     * @param suffixName 文件后缀名
     * @return boolean
     */
    public boolean putObject(String bucketName, String objectName, InputStream stream, String suffixName) {
        //boolean flag = bucketExists(bucketName);
        try {
            PutObjectOptions options = new PutObjectOptions(stream.available(), -1);
            boolean imageBoolean = ".webp".equals(suffixName) || ".jpg".equals(suffixName) || ".png".equals(suffixName)
                    || ".bmp".equals(suffixName) || ".jfif".equals(suffixName) || ".jpeg".equals(suffixName)
                    || ".tiff".equals(suffixName) || ".raw".equals(suffixName);
            if (imageBoolean){
                options.setContentType("image/jpeg");
            }

            minioClient.putObject(bucketName, objectName, stream, options);

            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                return true;
            }
        } catch (Exception e) {
            log.error("通过InputStream上传对象异常", e);
            return false;
        }
        return false;
    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return InputStream
     */
    public InputStream getObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                try {
                    return minioClient.getObject(bucketName, objectName);
                } catch (Exception e) {
                    log.error("以流的形式获取一个文件对象异常", e);
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 以流的形式获取一个文件对象（断点下载）
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param offset     起始字节的位置
     * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
     * @return InputStream
     */
    public InputStream getObject(String bucketName, String objectName, long offset, Long length) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                try {
                    return minioClient.getObject(bucketName, objectName, offset, length);
                } catch (Exception e) {
                    log.error("以流的形式获取一个文件对象（断点下载）异常", e);
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 下载并将文件保存到本地
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name
     * @return boolean
     */
    public boolean getObject(String bucketName, String objectName, String fileName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                try {
                    minioClient.getObject(bucketName, objectName, fileName);
                    return true;
                } catch (Exception e) {
                    log.error("下载并将文件保存到本地异常", e);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 删除一个对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return boolean
     */
    public boolean removeObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        try {
            if (flag) {
                minioClient.removeObject(bucketName, objectName);
                return true;
            }
        } catch (Exception e) {
            log.error("删除一个对象异常", e);
            return false;
        }
        return false;
    }

    /**
     * 删除指定桶的多个文件对象,返回删除错误的对象列表，全部删除成功，返回空列表
     *
     * @param bucketName  存储桶名称
     * @param objectNames 含有要删除的多个object名称的迭代器对象
     * @return List<String>
     */
    public List<String> removeObject(String bucketName, List<String> objectNames) {
        List<String> deleteErrorNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                Iterable<Result<DeleteError>> results = minioClient.removeObjects(bucketName, objectNames);
                for (Result<DeleteError> result : results) {
                    //DeleteError error = result.get();
                    deleteErrorNames.add(result.get().objectName());
                }
            } catch (Exception e) {
                log.error("删除指定桶的多个文件对象异常", e);
                return deleteErrorNames;
            }
        }
        return deleteErrorNames;
    }

    /**
     * 生成一个给HTTP GET请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。
     * 这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return String
     */
    public String presignedGetObject(String bucketName, String objectName, Integer expires) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                    log.info("presignedGetObject异常" + expires + "expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
                    return "";
                }
                return minioClient.presignedGetObject(bucketName, objectName, expires);
            } catch (Exception e) {
                log.error("presignedGetObject异常", e);
                return "";
            }
        }
        return "";
    }

    /**
     * 生成一个给HTTP PUT请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。
     * 这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return String
     */
    public String presignedPutObject(String bucketName, String objectName, Integer expires) {
        // 检查存储桶是否存在
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            try {
                if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                    log.info(expires + "expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
                    return "";
                }
                url = minioClient.presignedPutObject(bucketName, objectName, expires);
            } catch (Exception e) {
                log.error("presignedPutObject异常", e);
                return "";
            }
        }
        return url;
    }

    /**
     * 获取对象的元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return ObjectStat
     */
    public ObjectStat statObject(String bucketName, String objectName) {
        // 检查存储桶是否存在
        boolean flag = bucketExists(bucketName);
        try {
            if (flag) {
                return minioClient.statObject(bucketName, objectName);
            }
        } catch (Exception e) {
            log.error("获取对象的元数据异常", e);
            return null;
        }
        return null;
    }

    /**
     * 获取文件访问路径
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return String URL
     */
    public String getObjectUrl(String bucketName, String objectName) {
        // 检查存储桶是否存在
        boolean flag = bucketExists(bucketName);
        try {
            if (flag) {
                return minioClient.getObjectUrl(bucketName, objectName);
            }
        } catch (Exception e) {
            log.error("获取文件访问路径异常", e);
            return "";
        }
        return "";
    }
}
