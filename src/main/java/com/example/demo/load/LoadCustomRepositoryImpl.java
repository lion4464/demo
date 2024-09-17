package com.example.demo.load;

import com.example.demo.generic.SearchCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@Repository
public class LoadCustomRepositoryImpl implements LoadCustomRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public LoadCustomRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Flux<LoadFullDto> findAllLoadWithDriver(PageRequest pageRequest, List<SearchCriteria> searchCriteria) {

        String sql = "SELECT l.load_number AS loadNumber," +
                " l.pickup_address AS pickUpAddress, " +
                "l.delivery_address AS deliveryAddress," +
                " l.status AS loadStatus, " +
                "d.name AS driverName," +
                " d.surname AS driverSurname," +
                " d.email AS driverEmail," +
                " d.phone AS driverPhone " +
                "FROM loads l " +
                "LEFT JOIN  drivers d ON l.driver_id = d.id " +
                "WHERE l.deleted = false ";

        if (Objects.nonNull(searchCriteria)){
            if (!searchCriteria.isEmpty())
                sql += "AND " + buildWhereClause(searchCriteria);
        }

        sql += " ORDER BY l." + pageRequest.getSort().toString().replace(":", " ");
        sql += " LIMIT " + pageRequest.getPageSize() + " OFFSET " + pageRequest.getOffset();

        return r2dbcEntityTemplate.getDatabaseClient().sql(sql)
                .map((row, rowMetadata) -> new LoadFullDto(
                        row.get("driverName", String.class),
                        row.get("driverSurname", String.class),
                        row.get("driverEmail", String.class),
                        row.get("driverPhone", String.class),
                        row.get("loadNumber", String.class),
                        row.get("pickUpAddress", String.class),
                        row.get("deliveryAddress", String.class),
                        row.get("loadStatus", String.class)
                ))
                .all();
    }

    private String buildWhereClause(List<SearchCriteria> searchCriteria) {
        StringBuilder whereClause = new StringBuilder();
        for (SearchCriteria sc : searchCriteria) {
            String key = sc.getKey();
            String tableAlias = "l";


            if (key.contains("->")) {
                String[] splitKey = key.split("->");
                String entity = splitKey[0];
                String field = splitKey[1];

                if ("delivery".equalsIgnoreCase(entity)) {
                    tableAlias = "d";
                    key = field;
                }
            }

            switch (sc.getOperation()) {
                case "=":
                    whereClause.append(tableAlias).append(".").append(key).append(" = '").append(sc.getValue()).append("' AND ");
                    break;
                case "!=":
                    whereClause.append(tableAlias).append(".").append(key).append(" != '").append(sc.getValue()).append("' AND ");
                    break;
                case "<":
                    whereClause.append(tableAlias).append(".").append(key).append(" < '").append(sc.getValue()).append("' AND ");
                    break;
                case ">":
                    whereClause.append(tableAlias).append(".").append(key).append(" > '").append(sc.getValue()).append("' AND ");
                    break;
                case "<=":
                    whereClause.append(tableAlias).append(".").append(key).append(" <= '").append(sc.getValue()).append("' AND ");
                    break;
                case ">=":
                    whereClause.append(tableAlias).append(".").append(key).append(" >= '").append(sc.getValue()).append("' AND ");
                    break;
                case ":":
                    whereClause.append(tableAlias).append(".").append(key).append(" LIKE '%").append(sc.getValue()).append("%' AND ");
                    break;
            }
        }
        if (whereClause.length() > 0) {
            whereClause.setLength(whereClause.length() - 5);
        }
        return whereClause.toString();
    }
}

