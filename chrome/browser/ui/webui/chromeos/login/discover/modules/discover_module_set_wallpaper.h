// Copyright 2018 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

#ifndef CHROME_BROWSER_UI_WEBUI_CHROMEOS_LOGIN_DISCOVER_MODULES_DISCOVER_MODULE_SET_WALLPAPER_H_
#define CHROME_BROWSER_UI_WEBUI_CHROMEOS_LOGIN_DISCOVER_MODULES_DISCOVER_MODULE_SET_WALLPAPER_H_

#include <memory>

#include "base/macros.h"
#include "chrome/browser/ui/webui/chromeos/login/discover/discover_module.h"

namespace chromeos {

class DiscoverModuleSetWallpaper : public DiscoverModule {
 public:
  // Module name.
  static constexpr char kModuleName[] = "setWallpaper";

  DiscoverModuleSetWallpaper();
  ~DiscoverModuleSetWallpaper() override;

  // DiscoverModule:
  bool IsCompleted() const override;
  std::unique_ptr<DiscoverHandler> CreateWebUIHandler(
      JSCallsContainer* js_calls_container) override;

 private:
  DISALLOW_COPY_AND_ASSIGN(DiscoverModuleSetWallpaper);
};

}  // namespace chromeos

#endif  // CHROME_BROWSER_UI_WEBUI_CHROMEOS_LOGIN_DISCOVER_MODULES_DISCOVER_MODULE_SET_WALLPAPER_H_
