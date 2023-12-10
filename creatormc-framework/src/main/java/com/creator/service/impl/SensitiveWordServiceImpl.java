package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.dao.SensitiveWordDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.GetPageSensitiveListDto;
import com.creator.domain.entity.SensitiveWord;
import com.creator.domain.vo.PageVo;
import com.creator.domain.vo.SensitiveAdminVo;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.exception.SystemException;
import com.creator.service.SensitiveWordService;
import com.creator.utils.BeanCopyUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * (SensitiveWord)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 16:14:47
 */
@SuppressWarnings("rawtypes")
@Service("sensitiveWordService")
public class SensitiveWordServiceImpl extends ServiceImpl<SensitiveWordDao, SensitiveWord> implements SensitiveWordService {

    @Override
    public ResponseResult getPageSensitiveList(Integer pageNum, Integer pageSize, GetPageSensitiveListDto dto) {
        Page<SensitiveWord> page = new Page<>(pageNum, pageSize);
        page(page, new LambdaQueryWrapper<SensitiveWord>()
                .like(StringUtils.hasText(dto.getContent()), SensitiveWord::getContent, dto.getContent())
                .eq(StringUtils.hasText(dto.getType()), SensitiveWord::getType, dto.getType())
        );
        List<SensitiveWord> sensitiveWords = page.getRecords();
        return ResponseResult.okResult(new PageVo(sensitiveWords, page.getTotal()));
    }

    @Override
    public ResponseResult addSensitive(SensitiveWord sensitiveWord) {
        try {
            save(sensitiveWord);
        } catch (DuplicateKeyException e) {
            throw new SystemException(AppHttpCodeEnum.SENSITIVE_WORD_EXISTED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateSensitive(SensitiveWord sensitiveWord) {
        try {
            updateById(sensitiveWord);
        } catch (DuplicateKeyException e) {
            throw new SystemException(AppHttpCodeEnum.SENSITIVE_WORD_EXISTED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteSensitive(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getSensitive(Long id) {
        SensitiveWord sensitiveWord = getById(id);
        SensitiveAdminVo sensitiveAdminVo = BeanCopyUtils.copyBean(sensitiveWord, SensitiveAdminVo.class);
        return ResponseResult.okResult(sensitiveAdminVo);
    }
}

