package vip.cdms.arkreader.data;

import vip.cdms.arkreader.resource.AppOperator;
import vip.cdms.arkreader.resource.AppScore;

interface IResourceAccessor {
    AppScore getScore();

    AppOperator getOperator();
}
