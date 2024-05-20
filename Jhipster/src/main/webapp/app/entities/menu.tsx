import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/jhipster">
        <Translate contentKey="global.menu.entities.jhipster" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/jhipster-setting">
        <Translate contentKey="global.menu.entities.jhipsterSetting" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
