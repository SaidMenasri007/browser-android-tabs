// Copyright 2019 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

#ifndef IOS_PUBLIC_PROVIDER_CHROME_BROWSER_OVERRIDES_PROVIDER_H_
#define IOS_PUBLIC_PROVIDER_CHROME_BROWSER_OVERRIDES_PROVIDER_H_

// Provider for installing overrides.
class OverridesProvider {
 public:
  OverridesProvider();
  virtual ~OverridesProvider();

  virtual void InstallOverrides();
};

#endif  // IOS_PUBLIC_PROVIDER_CHROME_BROWSER_OVERRIDES_PROVIDER_H_
