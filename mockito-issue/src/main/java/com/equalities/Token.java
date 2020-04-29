package com.equalities;

import java.time.Instant;

public interface Token {
  String getAccessTokenValue();
  Instant getExpiresAt();
  Instant getIssuedAt();
}