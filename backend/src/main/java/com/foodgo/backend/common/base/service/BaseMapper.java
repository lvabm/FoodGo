package com.foodgo.backend.common.base.service;

public interface BaseMapper<Entity, CreateRequest, UpdateRequest, Response>
    extends ReadableMapper<Entity, Response>,
        CreatableMapper<Entity, CreateRequest>,
        UpdatableMapper<Entity, UpdateRequest> {}
