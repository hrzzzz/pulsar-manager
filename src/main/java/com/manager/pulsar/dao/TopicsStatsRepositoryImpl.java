/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.manager.pulsar.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.manager.pulsar.entity.TopicStatsEntity;
import com.manager.pulsar.entity.TopicsStatsRepository;
import com.manager.pulsar.mapper.TopicsStatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TopicsStatsRepositoryImpl implements TopicsStatsRepository {

    private final TopicsStatsMapper topicsStatsMapper;

    @Autowired
    public TopicsStatsRepositoryImpl(TopicsStatsMapper topicsStatsMapper) { this.topicsStatsMapper = topicsStatsMapper; }

    public long save(TopicStatsEntity topicStatsEntity) {
        topicsStatsMapper.insert(topicStatsEntity);
        return topicStatsEntity.getTopicStatsId();
    }

    public Optional<TopicStatsEntity> findMaxTime() {
        return Optional.ofNullable(topicsStatsMapper.findMaxTime());
    }

    public Page<TopicStatsEntity> findByClusterBroker(Integer pageNum, Integer pageSize,
                                                      String cluster, String broker, long timestamp) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TopicStatsEntity> topicStatsEntities = topicsStatsMapper.findByClusterBroker(cluster, broker, timestamp);
        return topicStatsEntities;
    }

    public Page<TopicStatsEntity> findByNamespace(Integer pageNum, Integer pageSize,
                                                  String cluster, String broker, String tenant,
                                                  String namespace, long timestamp) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TopicStatsEntity> topicStatsEntities= topicsStatsMapper.findByNamespace(cluster, broker, tenant, namespace, timestamp);
        return topicStatsEntities;
    }

    public Page<TopicStatsEntity> findByMultiTopic(Integer pageNum, Integer pageSize, String broker, String tenant,
                                                   String namespace, String persistent, List<String> topicList, long timestamp) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TopicStatsEntity> topicStatsEntities = topicsStatsMapper.findByMultiTopic(
                broker, tenant, namespace, persistent, topicList, timestamp);
        return topicStatsEntities;
    }

    public void remove(long timestamp, long timeInterval) {
        topicsStatsMapper.delete(timestamp, timeInterval);
    }
}
