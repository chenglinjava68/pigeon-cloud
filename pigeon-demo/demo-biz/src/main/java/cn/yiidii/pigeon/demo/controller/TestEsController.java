package cn.yiidii.pigeon.demo.controller;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.common.es.EsUtil;
import cn.yiidii.pigeon.common.es.dto.PageSearchDTO;
import cn.yiidii.pigeon.common.es.vo.EsPage;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试ES
 *
 * @author YiiDii Wang
 * @create 2021-06-30 21:43
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "测试ES接口")
@RequestMapping("/test/es")
public class TestEsController {

    private final EsUtil esUtil;

    @PostMapping("/index/{name}")
    @ApiOperation(value = "创建索引")
    @ApiOperationSupport(order = 1, author = "wang")
    public R createIndex(@PathVariable String name) {
        try {
            esUtil.createIndex(name);
        } catch (ElasticsearchStatusException e) {
            throw new BizException(StrUtil.format("索引{}已存在", name));
        } catch (IOException e) {
            log.error("e: {}", e);
            throw new BizException(StrUtil.format("索引{}创建失败", name));
        }
        return R.ok(StrUtil.format("索引[{}]创建成功", name));
    }

    @GetMapping("/index/list")
    @ApiOperation(value = "索引列表")
    @ApiOperationSupport(order = 2)
    @SneakyThrows
    public R<Object> indexList() {
        return R.ok(esUtil.findAllIndex());
    }

    @GetMapping("/index/{name}/exist")
    @ApiOperation(value = "索引是否存在")
    @ApiOperationSupport(order = 3)
    @SneakyThrows
    public R<Object> indexExist(@PathVariable String name) {
        final boolean exist = esUtil.isIndexExist(name);
        return R.ok(null, StrUtil.format("索引{}{}", name, exist ? "存在" : "不存在"));
    }

    @DeleteMapping("/index/{name}")
    @ApiOperation(value = "删除索引")
    @ApiOperationSupport(order = 4)
    @SneakyThrows
    public R<AcknowledgedResponse> deleteIndex(@PathVariable String name) {
        final AcknowledgedResponse acknowledgedResponse = esUtil.deleteIndex(name);
        return R.ok(acknowledgedResponse);
    }

    @GetMapping("/index/{name}/mapping")
    @ApiOperation(value = "查看索引映射")
    @ApiOperationSupport(order = 5)
    @SneakyThrows
    public R indexMapping(@PathVariable String name) {
        final Object mappings = esUtil.catIndexMappings(name);
        return R.ok(mappings);
    }

    @PostMapping("/index/{name}/data")
    @ApiOperation(value = "增加数据, 随机ID")
    @ApiOperationSupport(order = 6)
    @SneakyThrows
    public R addData(@PathVariable String name, @RequestBody JSONObject data) {
        final IndexResponse indexResponse = esUtil.addData(data, name);
        return R.ok(indexResponse);
    }

    @PostMapping("/index/{name}/data/{dataId}")
    @ApiOperation(value = "增加数据, 指定ID")
    @ApiOperationSupport(order = 7)
    @SneakyThrows
    public R addData(@PathVariable String name, @PathVariable String dataId, @RequestBody JSONObject data) {
        final IndexResponse indexResponse = esUtil.addData(data, name, dataId);
        return R.ok(indexResponse);
    }

    @DeleteMapping("/index/{name}/data/{dataId}")
    @ApiOperation(value = "指定ID删除数据")
    @ApiOperationSupport(order = 8)
    @SneakyThrows
    public R deleteDataById(@PathVariable String name, @PathVariable String dataId) {
        final DeleteResponse deleteResponse = esUtil.deleteDataById(name, dataId);
        return R.ok(deleteResponse);
    }

    @PutMapping("/index/{name}/data/{dataId}")
    @ApiOperation(value = "通过ID更新数据")
    @ApiOperationSupport(order = 9)
    @SneakyThrows
    public R updateDataById(@PathVariable String name, @PathVariable String dataId, @RequestBody JSONObject data) {
        final UpdateResponse updateResponse = esUtil.updateDataById(data, name, dataId);
        return R.ok(updateResponse);
    }

    @GetMapping("/index/{name}/data/{dataId}")
    @ApiOperation(value = "通过ID搜索数据")
    @ApiOperationSupport(order = 10)
    @SneakyThrows
    public R searchDataById(@PathVariable String name, @PathVariable String dataId, @RequestParam String fields) {
        final Map<String, Object> stringObjectMap = esUtil.searchDataById(name, dataId, fields);
        return R.ok(stringObjectMap);
    }

    @GetMapping("/index/{name}/data/{dataId}/exist")
    @ApiOperation(value = "通过ID判断文档是否存在")
    @ApiOperationSupport(order = 11)
    @SneakyThrows
    public R existsById(@PathVariable String name, @PathVariable String dataId) {
        final boolean b = esUtil.existsById(name, dataId);
        return R.ok(StrUtil.format("索引[{}]{}文档[{}]", name, b ? "存在" : "不存在", dataId));
    }

    @PostMapping("/index/{name}/data/batch")
    @ApiOperation(value = "批量插入数据")
    @ApiOperationSupport(order = 12)
    @SneakyThrows
    public R bulkPost(@PathVariable String name, @RequestBody List<Object> data) {
        final boolean b = esUtil.bulkPost(name, data);
        return R.ok(b ? "插入成功" : "插入失败");
    }

    @GetMapping("/index/{name}/geo/longitude/{longitude}/latitude/{latitude}/distance/{distance}")
    @ApiOperation(value = "geo搜索")
    @ApiOperationSupport(order = 13)
    @SneakyThrows
    public R geoDistanceQuery(@PathVariable String index, @PathVariable Float longitude, @PathVariable Float latitude, @PathVariable String distance) {
        final SearchResponse searchResponse = esUtil.geoDistanceQuery(index, longitude, latitude, distance);
        return R.ok(searchResponse);
    }

    @GetMapping("/index/{index}/{pageNo}/{pageSize}")
    @ApiOperation(value = "通过ID搜索数据")
    @ApiOperationSupport(order = 14)
    @SneakyThrows
    public R queryPageList(PageSearchDTO dto) {
        final EsPage esPage = esUtil.queryPageList(dto);
        return R.ok(esPage);
    }



}
